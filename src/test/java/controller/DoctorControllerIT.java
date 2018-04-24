package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoctorControllerIT {
    public static final String SSN_VALID = "1982600000000";
    public static final String NAME_VALID = "Ioana";
    public static final String ADDRESS_VALID = "adress";
    public static final String DIAG_VALID = "diag";
    public static final String DIAG_FILTER = "cancer";
    public static final String DATE_VALID = "03-04-2018";
    private Repository repository;
    private DoctorController controller;
    private ArrayList<String> meds;
    private Patient patient = new Patient(SSN_VALID, NAME_VALID, ADDRESS_VALID);

    public static final String CONS_ID = "cons1";

    @Before
    public void setUp() {
        String patients = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\PatientsTest.txt";
        String consultations = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\Consultation.txt";
        repository = new Repository(patients, consultations);
        controller = new DoctorController(repository);
    }

    /**
     * c) listing all the patients that have a certain disease, in descending order by the number
     * of consultations in the last year.
     */

    @Test
    public void tci0_filter() throws PatientException, ConsultationException {
        repository.cleanFiles();
        testAddPatient();
        testAddConsultation();
        testFilter_Empty();
    }

    @Test
    public void testFilter_Empty() throws PatientException {
        List<Patient> patients = controller.getPatientsWithDisease(DIAG_FILTER);
        assertEquals(patients.size(), 0);
    }

    public void testAddConsultation() throws ConsultationException, PatientException {
        initializeContextForConsultation();

        Consultation consultation = new Consultation(CONS_ID, patient.getSsn(), DIAG_VALID, meds, DATE_VALID);
        //WHEN
        System.out.println(controller.getPatientBySSN(patient.getSsn()));
        controller.addConsultation(consultation.getConsID(), consultation.getPatientSSN(), consultation.getDiag(),
            consultation.getMeds(), consultation.getConsultation_date());

        //THEN
        Boolean found = false;
        for (Consultation c : controller.getConsultationList()) {
            if (c.equals(consultation)) {
                found = true;
            }
        }
        assertTrue(found);
    }

    private void initializeContextForConsultation() throws PatientException {
        meds = new ArrayList<>();
        meds.add("med1");
        meds.add("med2");
    }

    @Test
    public void testAddPatient() throws PatientException {
        repository.cleanFiles();
        List<Patient> list = repository.getPatientList();
        controller.addPatient(patient);
        assertEquals(repository.getPatientList().size(), list.size() + 1);
    }
}