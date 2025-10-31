CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(250) NOT NULL
);

CREATE TABLE servicios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio FLOAT NOT NULL,
    duracion INT NOT NULL,
    imagenUrl VARCHAR(500),
    idCategoria INT,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (idCategoria) REFERENCES categorias(id) ON DELETE SET NULL
);


