package controller;

/**
 * enum RequestType : liste des types de requetes possibles
 */
public enum RequestType {
	CONNECT_USER,
	CONNECT_ADMIN,
	PASSWORD_FORGOTTEN,
	DISCONNECT,
	REGISTER,
	GET_PROFILE_INFORMATIONS,
	MODIFY_PROFILE,
	REMOVE_PROFILE,
	SEARCH_RIDE,
	ADD_WORKPLACE,
	GET_LIST_WORKPLACE,
	GET_LIST_USERS,
	DELETE_WORKPLACE,
	ADD_TOWN,
	GET_LIST_TOWN,
	DELETE_TOWN,
	GET_STAT_DRIVERS_PASSENGERS,
	GET_STAT_DRIVERS_PASSENGERS_PER_RIDE,
	GET_STAT_CONNECTIONS,
	GET_ADMIN_INFORMATIONS,
	MODIFY_ADMIN_PROFILE,
}
