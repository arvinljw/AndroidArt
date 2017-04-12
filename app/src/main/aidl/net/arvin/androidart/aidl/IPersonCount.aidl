package net.arvin.androidart.aidl;

import net.arvin.androidart.aidl.Person;
import net.arvin.androidart.aidl.IOnNewPersonIn;

interface IPersonCount {
    boolean addPerson(in Person person);

    List<Person> getPersons();

    void registerListener(in IOnNewPersonIn listener);

    void unregisterListener(in IOnNewPersonIn listener);
}
