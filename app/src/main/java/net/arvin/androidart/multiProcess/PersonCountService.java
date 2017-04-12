package net.arvin.androidart.multiProcess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import net.arvin.androidart.aidl.IOnNewPersonIn;
import net.arvin.androidart.aidl.IPersonCount;
import net.arvin.androidart.aidl.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * created by arvin on 17/2/15 22:48
 * email：1035407623@qq.com
 */
public class PersonCountService extends Service {
    private List<Person> persons;

    private IBinder mBinder = new IPersonCount.Stub() {
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

        }

        @Override
        public void unregisterListener(IOnNewPersonIn listener) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        persons = new ArrayList<>();
    }
}
