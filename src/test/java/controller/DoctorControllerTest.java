package controller;

import exceptions.PatientException;
import model.Patient;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorControllerTest {
    public static final String SSN_VALID = "1982600000000";
    public static final String NAME_VALID = "Ioana";
    public static final String ADDRESS_VALID = "adress";
    public static final String VERY_LONG_STRING =
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private Repository repository;
    private DoctorController controller;

    @Before
    public void setUp() {
        String patients = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\PatientsTest.txt";
        String consultations = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\Consultation.txt";
        repository = new Repository(patients, consultations);
        controller = new DoctorController(repository);
        repository.cleanFiles();
    }

    @Test
    public void tc1_testAddPatient() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient(SSN_VALID, NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    @Test(expected = PatientException.class)
    public void tc2_testAddPatientFails_null_fields() throws PatientException {
        Patient patient = new Patient(null, null, null);
        controller.addPatient(patient);
    }

    @Test(expected = PatientException.class)
    public void tc3_testAddPatientFails_empty_string_fields() throws PatientException {
        Patient patient = new Patient("", "", "");
        controller.addPatient(patient);
    }

    @Test(expected = PatientException.class)
    public void tc4_testAddPatientFails_ssn_too_long() throws PatientException {
        Patient patient = new Patient("12345678901234", NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);
    }

    @Test(expected = PatientException.class)
    public void tc5_testAddPatientFails_ssn_too_short() throws PatientException {
        Patient patient = new Patient("123456789012", NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);
    }

    @Test(expected = PatientException.class)
    public void tc6_testAddPatientFails_ssn_has_letters() throws PatientException {
        Patient patient = new Patient("aaaaaaaaaaaaa", NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);
    }

    @Test(expected = PatientException.class)
    public void tc7_testAddPatientFails_ssn_has_some_letters() throws PatientException {
        Patient patient = new Patient("a1234567890123", NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);
    }

    @Test
    public void tc8_testAddPatientFails_name_short() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient(SSN_VALID, "I", ADDRESS_VALID);
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    @Test
    public void tc9_testAddPatient_name_long() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient(SSN_VALID, VERY_LONG_STRING, ADDRESS_VALID);
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    @Test
    public void tc10_testAddPatient_address_short() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient(SSN_VALID, NAME_VALID, "A");
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    @Test
    public void tc11_testAddPatient_address_long() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient(SSN_VALID, NAME_VALID, VERY_LONG_STRING);
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }
}