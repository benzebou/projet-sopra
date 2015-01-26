package controller;


public class requestException extends Exception {
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		/**
		 * Code erreur
		 */
		private int errorCode;
	
		/**
		 * Constructeur avec parametre
		 * @param error : code erreur
		 */
		public requestException(int error){
			System.out.println("ATTENTION : Un code d'erreur a ete renvoye par le serveur");
			errorCode = error;
			getCodeReason();
		}
		
		/**
		 * Methode permettant de recuperer la raison de l'erreur produite
		 */
		private void getCodeReason(){
			String reason = "Code serveur : " + this.errorCode + " ";
			switch(errorCode){
			case(400):
				reason += "| BAD REQUEST : La syntaxe de la requete est erronee";
				break;
			
			case(401):
				reason += "| UNAUTHORIZED : Une authentification est necessaire pour acceder e la ressource";
				break;
				
			case(403):
				reason += "| FORBIDDEN : Le serveur a compris la requete, mais refuse de l'executer. Contrairement e l'erreur 401, s'authentifier ne fera aucune difference. Sur les serveurs oe l'authentification est requise, cela signifie generalement que l'authentification a ete acceptee mais que les droits d'acces ne permettent pas au client d'acceder e la ressource";
				break;
				
			case(404):
				reason += "| NOT FOUND : Ressource non trouvee";
				break;
				
			case(405):
				reason += "| REQUEST NOT ALLOWED : Methode de requete non autorisee";
				break;
			
			case(500):
				reason += "| ERROR SERVER : Probleme inconnu mais venant probablement d'une requete en base";
				break;
			}
			
			System.out.println(reason);
		}

}

/**
 * Liste des codes HTTP utilises :
 * - 200	OK	Requete traitee avec succes
 * - 201	Created	Requete traitee avec succes avec creation deun document
 * - 202	Accepted	Requete traitee mais sans garantie de resultat
 * - 204	No Content	Requete traitee avec succes mais pas deinformation e renvoyer
 * - 400	Bad Request	La syntaxe de la requete est erronee
 * - 401	Unauthorized	Une authentification est necessaire pour acceder e la ressource
 * - 403	Forbidden	Le serveur a compris la requete, mais refuse de l'executer. Contrairement e l'erreur 401, s'authentifier ne fera aucune difference. Sur les serveurs oe l'authentification est requise, cela signifie generalement que l'authentification a ete acceptee mais que les droits d'acces ne permettent pas au client d'acceder e la ressource
 * - 404	Not Found	Ressource non trouvee
 * - 405	Method Not Allowed	Methode de requete non autorisee
 * - 406	Not Acceptable	La ressource demandee n'est pas disponible dans un format qui respecterait les en-tetes "Accept" de la requete.
 */
