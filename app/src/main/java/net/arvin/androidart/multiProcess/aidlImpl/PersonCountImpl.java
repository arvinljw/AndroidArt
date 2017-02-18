package net.arvin.androidart.multiProcess.aidlImpl;

import android.os.RemoteException;

import net.arvin.androidart.aidl.IPersonCount;
import net.arvin.androidart.aidl.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * created by arvin on 17/2/18 17:54
 * email：1035407623@qq.com
 */
public class PersonCountImpl extends IPersonCount.Stub {
    private List<Person> persons;

    @Override
    public boolean addPerson(Person person) throws RemoteException {
        initData();
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

    private void initData() {
        if (persons == null) {
            persons = new ArrayList<>();
        }
    }

    @Override
    public List<Person> getPersons() throws RemoteException {
        initData();
        return persons;
    }
}
