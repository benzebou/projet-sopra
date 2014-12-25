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
		System.out.println("ATTENTION : Un code d'erreur a �t� renvoy� par le serveur");
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
			reason += "| BAD REQUEST : La syntaxe de la requ�te est erron�e";
			break;
		
		case(401):
			reason += "| UNAUTHORIZED : Une authentification est n�cessaire pour acc�der � la ressource";
			break;
			
		case(403):
			reason += "| FORBIDDEN : Le serveur a compris la requ�te, mais refuse de l'ex�cuter. Contrairement � l'erreur 401, s'authentifier ne fera aucune diff�rence. Sur les serveurs o� l'authentification est requise, cela signifie g�n�ralement que l'authentification a �t� accept�e mais que les droits d'acc�s ne permettent pas au client d'acc�der � la ressource";
			break;
			
		case(404):
			reason += "| NOT FOUND : Ressource non trouv�e";
			break;
		
		case(500):
			reason += "| ERROR SERVER : Probl�me inconnu mais venant probablement d'une requ�te en base";
			break;
		}
		
		System.out.println(reason);
	}

}

/**
* Liste des codes HTTP utilis�s :
* - 200	OK	Requ�te trait�e avec succ�s
* - 201	Created	Requ�te trait�e avec succ�s avec cr�ation d�un document
* - 202	Accepted	Requ�te trait�e mais sans garantie de r�sultat
* - 204	No Content	Requ�te trait�e avec succ�s mais pas d�information � renvoyer
* - 400	Bad Request	La syntaxe de la requ�te est erron�e
* - 401	Unauthorized	Une authentification est n�cessaire pour acc�der � la ressource
* - 403	Forbidden	Le serveur a compris la requ�te, mais refuse de l'ex�cuter. Contrairement � l'erreur 401, s'authentifier ne fera aucune diff�rence. Sur les serveurs o� l'authentification est requise, cela signifie g�n�ralement que l'authentification a �t� accept�e mais que les droits d'acc�s ne permettent pas au client d'acc�der � la ressource
* - 404	Not Found	Ressource non trouv�e
* - 405	Method Not Allowed	M�thode de requ�te non autoris�e
* - 406	Not Acceptable	La ressource demand�e n'est pas disponible dans un format qui respecterait les en-t�tes "Accept" de la requ�te.
*/

