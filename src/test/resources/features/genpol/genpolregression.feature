Feature: Operaciones en GENPOL

	@GENPOL @SC001
	Scenario Outline: Consulta de Poliza
		Given inicia la aplicación <data> <user>
		And inicia sesión en la aplicación
		When realiza la consulta de la póliza
		Then valida, guarda las evidencias
		Examples:
			| TC	| data	| user	|
			|TC-001	| 1		|	1	|
			#|TC-002	| 2		|	1	|
			#|TC-003	| 3		|	1	|
#			|TC-004	| 4		|	1	|
			#|TC-005	| 5		|	1	|
			##|TC-006	| 6		|	1	|
			#|TC-007	| 7		|	1	|
			#|TC-008	| 8		|	1	|
			#|TC-009	| 9		|	1	|
			#|TC-010	| 10	|	1	|
			#|TC-011	| 11	|	1	|
			#|TC-012	| 12	|	1	|
			#|TC-013	| 13	|	1	|
			#|TC-014	| 14	|	1	|
			##|TC-015	| 15	|	1	|
			#|TC-016	| 16	|	1	|
			#|TC-017	| 17	|	1	|
			#|TC-018	| 18	|	1	|
			#|TC-019	| 19	|	1	|
			#|TC-020	| 20	|	1	|
			#|TC-021	| 21	|	1	|
			#|TC-022	| 22	|	1	|
			#|TC-023	| 23	|	1	|
			###|TC-024	| 24	|	1	|
			#|TC-025	| 25	|	1	|
			##|TC-026	| 26	|	1	|
			#|TC-027	| 27	|	1	|
			#|TC-028	| 28	|	1	|
