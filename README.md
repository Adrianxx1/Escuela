Pruebas Postman — Sistema Escuela
PARTE 1 — REGISTROS (POST)

AULAS
POST http://localhost:8081/api/aulas

Registro 1
{
    "nombre": "Aula 201",
    "capacidad": 7
}

Registro 2


{
    "nombre": "Aula 202",
    "capacidad": 3
}

Registro 3


{
    "nombre": "Laboratorio C",
    "capacidad": 6
}

Registro 4


{
    "nombre": "Sala de Computo",
    "capacidad": 2
}



CURSOS
POST http://localhost:8081/api/cursos
Registro 1
{
    "nombre": "Calculo I",
    "descripcion": "Fundamentos de calculo diferencial",
    "creditos": 8
}

Registro 2


{
    "nombre": "Algebra Lineal",
    "descripcion": "Vectores y matrices",
    "creditos": 7
}

Registro 3
{
    "nombre": "Fisica II",
    "descripcion": "Electromagnetismo y ondas",
    "creditos": 6
}

Registro 4
{
    "nombre": "Estadistica",
    "descripcion": "Probabilidad y estadistica descriptiva",
    "creditos": 5
}




MAESTROS
POST http://localhost:8081/api/maestros

Registro 1
{
    "nombre": "Roberto",
    "apellidoPaterno": "Flores",
    "apellidoMaterno": "Mendoza",
    "email": "roberto.flores@escuela.com",
    "telefono": "5561234567"
}

Registro 2
{
    "nombre": "Patricia",
    "apellidoPaterno": "Reyes",
    "apellidoMaterno": "Castro",
    "email": "patricia.reyes@escuela.com",
    "telefono": "5572345678"
}

Registro 3
{
    "nombre": "Miguel",
    "apellidoPaterno": "Torres",
    "apellidoMaterno": "Vega",
    "email": "miguel.torres@escuela.com",
    "telefono": "5583456789"
}

Registro 4
{
    "nombre": "Sandra",
    "apellidoPaterno": "Ramirez",
    "apellidoMaterno": "Ortiz",
    "email": "sandra.ramirez@escuela.com",
    "telefono": "5594567890"
}




ALUMNOS
POST http://localhost:8081/api/alumnos

Registro 1
{
    "nombre": "Carlos",
    "apellidoPaterno": "Gonzalez",
    "apellidoMaterno": "Ramirez"
}

Registro 2
{
    "nombre": "Sofia",
    "apellidoPaterno": "Hernandez",
    "apellidoMaterno": "Morales"
}

Registro 3
{
    "nombre": "Diego",
    "apellidoPaterno": "Martinez",
    "apellidoMaterno": "Lopez"
}

Registro 4
{
    "nombre": "Valentina",
    "apellidoPaterno": "Perez",
    "apellidoMaterno": "Sanchez"
}


GRUPOS 
POST http://localhost:8081/api/grupos

Registro 1
{
    "idCurso": 1,
    "idMaestro": 1,
    "idAula": 1,
    "periodo": "2026-01"
}

Registro 2
{
    "idCurso": 2,
    "idMaestro": 2,
    "idAula": 3,
    "periodo": "2026-01"
}

Registro 3
{
    "idCurso": 3,
    "idMaestro": 3,
    "idAula": 4,
    "periodo": "2026-01"
}

Registro 4
{
    "idCurso": 4,
    "idMaestro": 4,
    "idAula": 2,
    "periodo": "2026-01"
}


HORARIOS
POST http://localhost:8081/api/horarios

Registro 1
{
    "idGrupo": 1,
    "dia": "Lunes",
    "horaInicio": "08:00",
    "horaFin": "10:00"
}

Registro 2
{
    "idGrupo": 1,
    "dia": "Miercoles",
    "horaInicio": "08:00",
    "horaFin": "10:00"
}

Registro 3
{
    "idGrupo": 2,
    "dia": "Martes",
    "horaInicio": "10:00",
    "horaFin": "12:00"
}

Registro 4
{
    "idGrupo": 3,
    "dia": "Jueves",
    "horaInicio": "12:00",
    "horaFin": "14:00"
}




INSCRIPCIONES  
POST http://localhost:8081/api/inscripciones
{
    "idAlumno": 4,
    "idGrupo": 1
}

Registro 2
POST http://localhost:8081/api/inscripciones
{
    "idAlumno": 3,
    "idGrupo": 1
}

Registro 3
POST http://localhost:8081/api/inscripciones
{
    "idAlumno": 5,
    "idGrupo": 2
}

Registro 4
POST http://localhost:8081/api/inscripciones
{
    "idAlumno": 4,
    "idGrupo": 3
}


CALIFICACIONES  
Registro 1
POST http://localhost:8081/api/calificaciones

{
    "idInscripcion": 3,
    "calificacion": 9.0
}

Registro 2
{
    "idInscripcion": 4,
    "calificacion": 8.5
}

Registro 3
{
    "idInscripcion": 1,
    "calificacion": 7.5
}

Registro 4
{
    "idInscripcion": 2,
    "calificacion": 8.0
}






