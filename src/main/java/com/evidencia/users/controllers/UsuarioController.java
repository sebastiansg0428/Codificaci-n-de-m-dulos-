package com.evidencia.users.controllers;

import com.evidencia.users.entities.Usuario;
import com.evidencia.users.interfaces.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Anotación que marca esta clase como un controlador REST (devuelve JSON)
@RestController

// Define la ruta base para todos los endpoints de este controlador
@RequestMapping("/api/usuarios")

// Genera automáticamente un constructor con todos los atributos requeridos (gracias a Lombok)
@AllArgsConstructor

public class UsuarioController {

    // Inyección de dependencias: el repositorio de usuarios
    private final UsuarioRepository repository;

    // Metodo GET para listar todos los usuarios registrados.
    // Endpoint: GET http://localhost:8080/api/usuarios/lista

    @GetMapping("/lista")
    public List <Usuario> listar (){
        return repository.findAll();
    }


    // Metodo POST para crear un nuevo usuario.
    // Endpoint: POST http://localhost:8080/api/usuarios
    // Ejemplo de JSON que se debe enviar:
    //{ "email": "usuario@correo.com",
    //"password": "12345"
    // }

    @PostMapping
    public Usuario createUser(@RequestBody Usuario usuario){
        return repository.save(usuario);
    }

    //Metodo DELETE para eliminar un usuario por su ID
    //Endpoint: DELETE http://localhost:8080/api/usuarios/{id}
    //Ejemplo: DELETE http://localhost:8080/api/usuarios/5

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return repository.findById(id).map(usuario -> {
            repository.delete(usuario);
            return "Usuario eliminado correctamente";
        }).orElse("Usuario no encontrado");
    }

    //Metodo PUT para actualizar un usuario existente.
    //Endpoint: PUT http://localhost:8080/api/usuarios/{id}
    //Ejemplo de JSON que se debe enviar:
    //{ "email": "nuevo@correo.com",
    //"password": "nuevaContraseña"
    // }

    @PutMapping("/{id}")
    public Usuario updateUser(@RequestBody Usuario usuario,@PathVariable Long id){

        return repository.findById(id).map(usuario1 -> {
            usuario1.setEmail(usuario.getEmail());
            usuario1.setPassword(usuario.getPassword());
            return repository.save(usuario1);

        }).orElse(null);

    }

}
