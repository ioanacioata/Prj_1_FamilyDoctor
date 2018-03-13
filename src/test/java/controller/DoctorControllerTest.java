package controller;

import exceptions.PatientException;
import model.Patient;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorControllerTest {
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
    public void testAddPatient() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient("test1", "1982600000000", "adress1");
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    @Test(expected = PatientException.class)
    public void testAddPatientFails() throws PatientException {
        Patient patient = new Patient("", "", "adress1");
        controller.addPatient(patient);
    }
}