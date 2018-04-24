package controller;

import exceptions.ConsultationException;
import exceptions.PatientException;
import model.Consultation;
import model.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DoctorControllerTest {
    public static final String SSN_VALID = "1982600000000";
    public static final String NAME_VALID = "Ioana";
    public static final String ADDRESS_VALID = "adress";
    public static final String VERY_LONG_STRING =
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    public static final String DIAG_VALID = "diag";
    public static final String ID_CONSULTATION_VALID = "id1";
    public static final String DATE_VALID = "03-04-2018";
    private Repository repository;
    private DoctorController controller;
    private ArrayList<String> meds;
    private Patient patient;

    @Before
    public void setUp() {
        String patients = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\PatientsTest.txt";
        String consultations = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\src\\test\\resources\\Consultation.txt";
        repository = new Repository(patients, consultations);
        controller = new DoctorController(repository);
        repository.cleanFiles();
    }

    @After
    public void setAfter() {
        repository.cleanFiles();
    }

    /*WBT*/
    @Test(expected = ConsultationException.class)
    public void tc1_wbt_testAddConsultation_null_meds() throws ConsultationException {
        controller.addConsultation(ID_CONSULTATION_VALID, SSN_VALID, DIAG_VALID, null, DATE_VALID);
    }

    @Test(expected = ConsultationException.class)
    public void tc2_wbt_testAddConsultation_other_null_fields() throws ConsultationException {
        controller.addConsultation(null, null, null, new ArrayList<>(), null);
    }

    @Test
    public void tc3_wbt_testAddConsultation_consultationExists() throws ConsultationException, PatientException {
        //GIVEN
        initializeContextForConsultation();

        String consID = "cons2";

        controller.addConsultation("cons1", patient.getSsn(), DIAG_VALID + "test", meds, DATE_VALID);
        controller.addConsultation(consID, patient.getSsn(), DIAG_VALID, meds, DATE_VALID);
        //WHEN
        try {
            controller.addConsultation(consID, patient.getSsn(), DIAG_VALID, meds, DATE_VALID);
            fail();
        } catch (ConsultationException ignored) {
        }
    }

    @Test
    public void tc5_wbt_testAddConsultation_ok() throws ConsultationException, PatientException {
        //GIVEN
        initializeContextForConsultation();

        String consID = "cons";

        controller.addConsultation(consID+"1", patient.getSsn(), DIAG_VALID + "test", meds, DATE_VALID);
        controller.addConsultation(consID+"2", patient.getSsn(), DIAG_VALID + "test", meds, DATE_VALID);

        //WHEN
        Consultation consultation = new Consultation(consID+"3", patient.getSsn(), DIAG_VALID, meds, DATE_VALID);
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

    @Test
    public void tc4_wbt_testAddConsultation_initialListEmpty() throws ConsultationException, PatientException {
        //GIVEN
        initializeContextForConsultation();

        String consID = "cons";

        Consultation consultation = new Consultation(consID, patient.getSsn(), DIAG_VALID, meds, DATE_VALID);
        //WHEN
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
        meds.add("meds2");

        patient = new Patient(SSN_VALID, NAME_VALID, ADDRESS_VALID);
        controller.addPatient(patient);
    }

    /*BBT*/
    @Test
    public void testAddPatient() throws PatientException {
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