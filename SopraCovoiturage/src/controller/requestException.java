package controller;

public class requestException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Code erreur de la requete
	 */
	private int errorCode;

	/**
	 * Constructeur avec parametre
	 * @param error : code erreur de la requete
	 */
	public requestException(int error){
		System.out.println("ATTENTION : Un code d'erreur a été renvoyé par le serveur");
		errorCode = error;
		getCodeReason();
	}
	
	/**
	 * Methode permettant de recuperer le code indiquant la cause de l'erreur provoquee lors de la requete
	 */
	private void getCodeReason(){
		String reason = "Code serveur : " + this.errorCode + " ";
		switch(errorCode){
		case(400):
			reason += "| BAD REQUEST : La syntaxe de la requête est erronée";
			break;
		
		case(401):
			reason += "| UNAUTHORIZED : Une authentification est nécessaire pour accéder à la ressource";
			break;
			
		case(403):
			reason += "| FORBIDDEN : Le serveur a compris la requête, mais refuse de l'exécuter. Contrairement à l'erreur 401, s'authentifier ne fera aucune différence. Sur les serveurs où l'authentification est requise, cela signifie généralement que l'authentification a été acceptée mais que les droits d'accès ne permettent pas au client d'accéder à la ressource";
			break;
			
		case(404):
			reason += "| NOT FOUND : Ressource non trouvée";
			break;
		
		case(500):
			reason += "| ERROR SERVER : Problème inconnu mais venant probablement d'une requête en base";
			break;
		}
		
		System.out.println(reason);
	}

}

/**
* Liste des codes HTTP utilisés :
* - 200	OK	Requête traitée avec succès
* - 201	Created	Requête traitée avec succès avec création d’un document
* - 202	Accepted	Requête traitée mais sans garantie de résultat
* - 204	No Content	Requête traitée avec succès mais pas d’information à renvoyer
* - 400	Bad Request	La syntaxe de la requête est erronée
* - 401	Unauthorized	Une authentification est nécessaire pour accéder à la ressource
* - 403	Forbidden	Le serveur a compris la requête, mais refuse de l'exécuter. Contrairement à l'erreur 401, s'authentifier ne fera aucune différence. Sur les serveurs où l'authentification est requise, cela signifie généralement que l'authentification a été acceptée mais que les droits d'accès ne permettent pas au client d'accéder à la ressource
* - 404	Not Found	Ressource non trouvée
* - 405	Method Not Allowed	Méthode de requête non autorisée
* - 406	Not Acceptable	La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes "Accept" de la requête.
*/

