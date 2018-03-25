
import repository.Repository;
import ui.DoctorUI;
import controller.DoctorController;

public class App {

	public static void main(String[] args) {
		String patients = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\FilePatients.txt";
		String consultations = "E:\\workspace\\vvss\\Lab 1\\Prj_1_FamilyDoctor\\FileConsultations.txt";
		Repository repo = new Repository(patients, consultations);
		DoctorController ctrl = new DoctorController(repo);
		
		DoctorUI console = new DoctorUI(ctrl);
		console.Run();
	}
}
