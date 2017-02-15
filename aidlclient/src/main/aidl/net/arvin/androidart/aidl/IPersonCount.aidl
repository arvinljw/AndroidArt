package net.arvin.androidart.aidl;

import net.arvin.androidart.aidl.Person;

interface IPersonCount {
    boolean addPerson(in Person person);

    List<Person> getPersons();
}
