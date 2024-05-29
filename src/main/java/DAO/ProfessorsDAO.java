package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import business.Professors;

public class ProfessorsDAO {
	// attributes 
	MaConnexion connexion;
	Statement stmt;
	Scanner sc = new Scanner(System.in);
	
	// Constructor
	public ProfessorsDAO(MaConnexion connexion) {
		this.connexion = connexion;
		stmt = connexion.getStmt();
	}
	
	// Methods
		// consulter les Modules du Professeur.
	public void consulterProfModule(int id) {
	    try {
	    		ArrayList<Integer> ids=idsModuleProf(id);
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
		    	System.out.println("||||||||||||||||||||||||||||||||||||||||| Liste des Modules de Prof ||||||||||||||||||||||||||||||||||||||||||||||");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	    		for(int id1 : ids) {
	            ResultSet result_module = stmt.executeQuery("SELECT * FROM module WHERE id='" + id1 + "'");
	            while (result_module.next()) {
	                System.out.println("| ID : " + result_module.getInt("id") + " | Nom : " + result_module.getString("name"));
	            }
	    	}
	    } catch (SQLException e) {	
	    System.out.println("+----------------------------------------------------------------------------------------------------------------+");
    	System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
		System.out.println("|                                          LISTE VIDE        -_-                                                 |");
    	System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
		System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");
	    }
	}
		// id des professeurs.
	public ArrayList<Integer> idsModuleProf(int id) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM prof_module WHERE professor_id='" + id + "'");
        ArrayList<Integer> ids = new ArrayList<>();
        while(resultSet.next()) {
        	ids.add(resultSet.getInt("module_id"));
        } // module vide 
        return ids;
	}
	
		// consulter les classes du professeurs.
	public void consulterProfClasse(int id) {
		 try {
	    		ArrayList<Integer> ids=idsModuleProf(id);
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
		    	System.out.println("|||||||||||||||||||||||||||||||||||||||||| Liste des Classes de Prof |||||||||||||||||||||||||||||||||||||||||||||");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	    		for(int id1 : ids) {
		    		ArrayList<Integer> ids2=idsClasseProf(id1);
		    		for(int id2 : ids2) {
		    			ResultSet result_classe = stmt.executeQuery("SELECT * FROM classe WHERE id='" + id2 + "'");
			            while (result_classe.next()) {
			                System.out.println("| ID : " + result_classe.getInt("id") + " | Nom : " + result_classe.getString("name")+ " | Filiere : " + result_classe.getString("filliere")+ " | Grade : " + result_classe.getString("grade"));
			            }
		    		}
	    	}
	    } catch (SQLException e) {
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("|                                            LISTE VIDE        -_-                                               |");
				System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");	   
	    }
	}
		// id des classes.
	public ArrayList<Integer> idsClasseProf(int id) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM classe_module WHERE module_id='" + id + "'");
        ArrayList<Integer> ids = new ArrayList<>();
        while(resultSet.next()) {
        	ids.add(resultSet.getInt("classe_id"));
        }
        return ids;
	}
	
		// affecter les notes des etudiants
		public void affecterStudentNote(int id) throws SQLException {
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	    	System.out.println("||||||||||||||||||||||||||||||||||| Affectation de la note a un etudiant |||||||||||||||||||||||||||||||||||||||||");
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			char reponse='o';
			while(reponse=='o') {
        	  consulterProfModule(id);
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
              System.out.print("| Choisir l'id de Module a affecter ces notes : ");
              int id_module = sc.nextInt();
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
              if(isModuleExists(id_module)==true) {
                  consulterStudentsProf(id,id_module);
      			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
                  System.out.print("| Choisir L'id de l'etudiant pour affecter sa note du module : ");
              } else {
      			System.out.println("!!!!!!!! ID n'existe Pas -_- ");
      			affecterStudentNote(id);
      		}
              int id_student = sc.nextInt();
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
              System.out.print("| Entrer la note de Module : ");
              float note_module = sc.nextFloat();
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");

                try {
                    stmt.executeUpdate("INSERT INTO student_module (student_id, module_id,note_module) VALUES (" + id_student + ", " + id_module + ", " + note_module +")");
                } catch (SQLException e) {
					System.out.println("+----------------------------------------------------------------------------------------------------------------+");
					System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
					System.out.println("|                                      NOTE EXISTE  DEJA   -_-                                                   |");
					System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
					System.out.println("+----------------------------------------------------------------------------------------------------------------+");
					System.out.print("| Si vous voulez modifier la note tapez sur 'o' : ");
					char reponse_note=sc.next().charAt(0);
					System.out.println("+----------------------------------------------------------------------------------------------------------------+");
					if(reponse_note=='o'){
						modifierNote(id,id_student,id_module);
					}
                }
                System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
              System.out.println("| + => la note a bien affecter. ;)");
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
              System.out.print("| Voulez vous affecter une note a un autre etudiant ? ( 'o' pour oui / 'une autre charactere' pour non )  : ");
              reponse=sc.next().charAt(0);
  			  System.out.println("+----------------------------------------------------------------------------------------------------------------+");
        }
      
	}

	private void modifierNote(int id,int id_student,int id_module) throws SQLException {
		System.out.println("+----------------------------------------------------------------------------------------------------------------+");
		System.out.println("|||||||||||||||||||||||||||||||||||||| Modification de la note de l'etudiant |||||||||||||||||||||||||||||||||||||");
		System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");
		System.out.println("+----------------------------------------------------------------------------------------------------------------+");
		System.out.print("| Entrer la note de Module : ");
		float note_module = sc.nextFloat();
		System.out.println("+----------------------------------------------------------------------------------------------------------------+");

			stmt.executeUpdate("UPDATE student_module SET note_module = '" + note_module + "' WHERE student_id ='" + id_student+"' AND module_id='"+id_module+"'");

	}


	// id des etudiants
	public ArrayList<Integer> idsStudentModule(int id) throws SQLException {
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM class_student WHERE class_id='" + id + "'");
		ArrayList<Integer> ids = new ArrayList<>();
        while(resultSet.next()) {
        	ids.add(resultSet.getInt("student_id"));
        }
        return ids;
	}
			// consulter les etudiants de professeur.
	public void consulterStudentsProf(int id,int id_module) {
		
		try {
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	    	System.out.println("|||||||||||||||||||||||||||||||||||||||||||| Liste de vos Etudiants ||||||||||||||||||||||||||||||||||||||||||||||");
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
    		ArrayList<Integer> ids2=idsClasseProf(id_module);
    		for(int id2 : ids2) {
    			ArrayList<Integer> ids3 = idsStudentModule(id2);
    			for(int id3 : ids3) {
    				ResultSet result_student = stmt.executeQuery("SELECT * FROM student WHERE id='" + id3 + "'");
		            while (result_student.next()) {
		                System.out.println("| ID : " + result_student.getInt("id") + " | Nom : " + result_student.getString("name")+ " | last name : " + result_student.getString("last_name"));
		            }
    			}	
    		}
	    } catch (SQLException e) {
	    	System.out.println("+----------------------------------------------------------------------------------------------------------------+");
		    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
			System.out.println("|                                           LISTE VIDE        -_-                                                |");
			System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
			System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");	   
    	    }
	}
			// id des modules existes
	public boolean isModuleExists(int id_module) {
	    boolean moduleExists = false;
	    try {
	        ResultSet result_module = stmt.executeQuery("SELECT * FROM module WHERE ID='" + id_module + "'");
	        if (result_module.next()) {
	            moduleExists = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return moduleExists;
	}

	
		// affecter l'abscence a l'etudiant
	public void affecterStudentAbscence(int id) throws SQLException {

		System.out.println("+----------------------------------------------------------------------------------------------------------------+");
    	System.out.println("|||||||||||||||||||||||||||||||||||||||||||| Affectation de l'abscence |||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("+----------------------------------------------------------------------------------------------------------------+");

		char reponse='o';
		while(reponse=='o') {
	        consulterProfModule(id);
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        System.out.print("| Choisir l'id de Module a affecter l'abscence : ");
	        int id_module = sc.nextInt();
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        if(isModuleExists(id_module)==true) {
	            consulterStudentsProf(id,id_module);
	    		System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	            System.out.print("| Choisir L'id de l'etudiant pour affecter son abscence dans ce module : ");
	        } else {
	        	System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("|                                           ID INVALIDE       -_-                                                |");
				System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");	   
				affecterStudentAbscence(id);
			}
	        int id_student = sc.nextInt();
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        System.out.print("| Entrer les heurs de l'abscence de cet etudiant dans ce module  (entrer 1 heur et 4 heurs ): ");
	        int nombre_abscence = sc.nextInt();
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        if(nombre_abscence <= 4 && nombre_abscence >= 1 ) {
	            stmt.executeUpdate("UPDATE student SET abscence_hours = abscence_hours + " + nombre_abscence + " WHERE ID='" + id_student +"'");
				System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
	            System.out.println("| + => l'abscence a bien affecter.  ;) ");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");
	        }else {
	        	System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("|                      vous avez depassez le nombre des heurs entrer 1 et 4. !-_-!                               |");
				System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
				System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");	   
	        }
			System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
	        System.out.print("| => Voulez vous affecter un autre abscence ? : ");
	        reponse=sc.next().charAt(0);
			System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");
		}
	}


		// consulter les informations du prof
	public void consulterInformations(int id) throws SQLException {

		System.out.println("+----------------------------------------------------------------------------------------------------------------+");
    	System.out.println("|||||||||||||||||||||||||||||||||||||||||||| Vos Informations ||||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("+----------------------------------------------------------------------------------------------------------------+");


	    ResultSet resultat_prof = stmt.executeQuery("SELECT * FROM prof WHERE ID='" + id + "'");
	    if (resultat_prof.next()) {
	        Professors prof = new Professors(resultat_prof.getString("username"),
	                                      resultat_prof.getString("password"),
	                                      resultat_prof.getString("name"),
	                                      resultat_prof.getString("last_name"),
	                                      resultat_prof.getString("address"),
	                                      resultat_prof.getString("sex"),
	                                      resultat_prof.getInt("age"),
	                                      resultat_prof.getString("cne_prof"));
	        System.out.print(prof);
	        ArrayList<String> modules = prof_modules(id);
	        for(String module : modules) {
	        	System.out.print(" | Modules : "+module);
	        }

			System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
	        System.out.print("| Voulez-vous changer les informations ? (o pour oui / n pour non ) :");
	        String reponse = sc.nextLine();
			System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        if ("o".equals(reponse)) {
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	        	 System.out.print("| Choisir le champ à modifier (1:username, 2:password, 3: Nom, 4: Prenom, 5: Adresse, 6: Sexe, 7: Age, 8: CNE Prof) : ");
	 	        int reponse_champ = sc.nextInt();
				System.out.println("+----------------------------------------------------------------------------------------------------------------+");
	 	        sc.nextLine();
	        	switch (reponse_champ) {
				case 1:
					System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					System.out.print("| Entrer le nouveau username : ");
			        String username = sc.nextLine();
					System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			        stmt.executeUpdate("UPDATE prof SET username='" + username + "' WHERE ID='" + id + "'");
					break;
				case 2:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau password : ");
			         String password = sc.nextLine();
					 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET password='" + password + "' WHERE ID='" + id + "'");
					break;
				case 3:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau nom : ");
			         String name = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET name='" + name + "' WHERE ID='" + id + "'");
					break;
				case 4:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau prenom : ");
			         String prenom = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET last_name='" + prenom + "' WHERE ID='" + id + "'");
					break;
				case 5:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau address : ");
			         String address = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET address='" + address + "' WHERE ID='" + id + "'");
					break;
				case 6:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le sex : ");
			         String sex = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET sex='" + sex + "' WHERE ID='" + id + "'");
					break;
				case 7:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau age : ");
			         String age = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET age='" + age + "' WHERE ID='" + id + "'");
					break;
				case 8:
				   	 System.out.println("\n+----------------------------------------------------------------------------------------------------------------+");
					 System.out.print("| Entrer le nouveau CNE : ");
			         String cne_prof = sc.nextLine();
				   	 System.out.println("+----------------------------------------------------------------------------------------------------------------+");
			         stmt.executeUpdate("UPDATE prof SET cne_prof='" + cne_prof + "' WHERE ID='" + id + "'");
					break;
				default:
					System.out.println("+----------------------------------------------------------------------------------------------------------------+");
				    System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
					System.out.println("|                                      CHAMP INVALIDE     -_-                                                    |");
					System.out.println("|!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|");
					System.out.println("+----------------------------------------------------------------------------------------------------------------+\n");	   
					break;
				}
	        }
	    }
	}
	
	public ArrayList<String> prof_modules(int id) throws SQLException {
		ArrayList<String> modules=new ArrayList<>();
		ArrayList<Integer> ids = idsModuleProf(id);
		for(int id1 : ids) {
			ResultSet result_module =  stmt.executeQuery("SELECT * FROM module WHERE ID='" + id1 + "'");
			while(result_module.next()) {
				modules.add(result_module.getString("name"));
			}
		}
		return modules;
	}
}
