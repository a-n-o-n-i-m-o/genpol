Feature: prueba de adherencia

	@GENPOL-SmokeTest
	Scenario Outline: Prueba de humo de los scripts
		Given que el usuario se logue <user>
		When pasamos portodos los campos de los aplicativos
		Then valida los distintos modulos de consulta

		Examples:
			| user |
			| 1    |