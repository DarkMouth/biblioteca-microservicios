CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_libros;
CREATE DATABASE IF NOT EXISTS db_autores;
CREATE DATABASE IF NOT EXISTS db_categorias;
CREATE DATABASE IF NOT EXISTS db_prestamos;
CREATE DATABASE IF NOT EXISTS db_reservas;
CREATE DATABASE IF NOT EXISTS db_multas;
CREATE DATABASE IF NOT EXISTS db_sedes;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
CREATE DATABASE IF NOT EXISTS db_catalogacion;

GRANT ALL PRIVILEGES ON db_usuarios.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_libros.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_autores.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_categorias.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_prestamos.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_reservas.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_multas.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_sedes.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_notificaciones.* TO 'biblioteca'@'%';
GRANT ALL PRIVILEGES ON db_catalogacion.* TO 'biblioteca'@'%';

FLUSH PRIVILEGES;