package net.arvin.androidart.multiProcess.aidlImpl;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import net.arvin.androidart.aidl.IOnNewPersonIn;
import net.arvin.androidart.aidl.IPersonCount;
import net.arvin.androidart.aidl.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * created by arvin on 17/2/18 17:54
 * email：1035407623@qq.com
 */
public class PersonCountImpl extends IPersonCount.Stub {
    private AtomicBoolean isServiceDestroyed;

    private CopyOnWriteArrayList<Person> persons;

    private RemoteCallbackList<IOnNewPersonIn> mListeners;

    public PersonCountImpl() {
        isServiceDestroyed = new AtomicBoolean(false);
        initData();
        initListeners();
        new Thread(new ServiceWorker()).start();
    }

    private void initData() {
        if (persons == null) {
            persons = new CopyOnWriteArrayList<>();
        }
    }

    private void initListeners() {
        if (mListeners == null) {
            mListeners = new RemoteCallbackList<>();
        }
    }

    @Override
    public boolean addPerson(Person person) throws RemoteException {
        boolean isExit = false;
        for (Person entity : persons) {
            if (entity.getName().equals(person.getName())) {
                isExit = true;
                break;
            }
        }
        if (!isExit) {
            persons.add(person);
        }
        return isExit;
    }

    @Override
    public List<Person> getPersons() throws RemoteException {
        return persons;
    }

    @Override
    public void registerListener(IOnNewPersonIn listener) throws RemoteException {
        mListeners.register(listener);
    }

    @Override
    public void unregisterListener(IOnNewPersonIn listener) throws RemoteException {
        mListeners.unregister(listener);
    }

    private void onNewPersonIn(Person newPerson) {
        int size = mListeners.beginBroadcast();
        for (int i = 0; i < size; i++) {
            IOnNewPersonIn onNewPersonIn = mListeners.getBroadcastItem(i);
            try {
                if (onNewPersonIn != null) {
                    onNewPersonIn.onNewPersonIn(newPerson);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListeners.finishBroadcast();
    }

    public void onDestroy() {
        isServiceDestroyed.set(true);
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            while (!isServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Person newPerson = new Person();
                newPerson.setAge(24);
                newPerson.setName("nick");
                onNewPersonIn(newPerson);
            }
        }
    }

}
