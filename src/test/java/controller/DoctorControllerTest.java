package controller;

import exceptions.PatientException;
import junit.framework.TestCase;
import model.Patient;
import org.junit.Before;
import repository.Repository;

import java.util.List;

public class DoctorControllerTest extends TestCase{
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

    public void testAddPatient() throws PatientException {
        List<Patient> list = repository.getPatientList();

        Patient patient = new Patient("test1", "1982600000000", "adress1");
        controller.addPatient(patient);

        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }

    public void testAddPatientFails() throws PatientException {

        Patient patient = new Patient("", "", "adress1");
        try{
            controller.addPatient(patient);
            fail();
        }catch (PatientException e){
        }
    }
}