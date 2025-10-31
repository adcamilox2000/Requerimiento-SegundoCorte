-- Insertar categorías iniciales
INSERT INTO categorias (nombreCategoria) VALUES
('Corte');
INSERT INTO categorias (nombreCategoria) VALUES
('Barba');
INSERT INTO categorias (nombreCategoria) VALUES
('Tinte');
INSERT INTO categorias (nombreCategoria) VALUES
('Diseños');


-- Insertar servicios iniciales con imágenes
INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Low fade', 'Corte de cabello con degradado bajo', 20000, 45, 'https://i.pinimg.com/736x/53/66/49/536649d32e8bbb78451339090d780bc0.jpg', 1);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Burst fade', 'Corte de cabello con degradado en forma de u', 20000, 45, 'https://www.barberstake.com/wp-content/uploads/2025/07/Low-Burst-Fade.jpg', 1);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Mullet', 'Corte de cabello moderno', 20000, 45, 'https://www.vicisitudysordidez.com/wp-content/uploads/2025/05/64b89b3ad7940a14bfcfccd3_Mullet-Your-Way.png', 1);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Arreglo de Barba', 'Perfilado y recorte de barba', 10000, 20, 'https://blog.newoldman.com.br/wp-content/uploads/2018/12/Barba-degrade-Como-fazer-manter-e-imagens-para-inspirar-3.jpg', 2);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Barba y Bigote', 'Arreglo completo de barba', 15000, 30, 'https://i.pinimg.com/474x/0e/84/93/0e849311149e64d1c2e26ea4eed9a242.jpg', 2);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Rayitos', 'Tinturado de cabello en rayitos', 40000, 40, 'https://i.pinimg.com/736x/1c/9c/64/1c9c6442fd1034618af5786a9024973c.jpg', 3);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Tintura de Cabello', 'Coloración de cabello', 50000, 90, 'https://i.pinimg.com/736x/4b/ed/8e/4bed8e1ceacdbde960e369f7c07b2e28.jpg', 3);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Diseño en Cejas', 'Diseño en la ceja', 8000, 10, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5RKezSZ3-H5_EHgeePs0glsVQyHDcpdBIWA&s', 4);



INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Corte Infantil', 'Corte de cabello para niños', 18000, 40, 'https://www.todofamilias.com/wp-content/uploads/2024/01/flequillo_Arriba_1.jpg', 1);

INSERT INTO servicios (NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA) VALUES 
('Diseño en Cabello', 'Diseños en el cabello', 10000, 20, 'https://i.ytimg.com/vi/uZRrYcw4wUc/mqdefault.jpg', 4);