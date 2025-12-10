from flask import Flask, render_template, request, redirect, url_for
from flask_mysqldb import MySQL
from datetime import datetime
import os

app = Flask(__name__)

# -------------------------------------------------
# CONFIGURACIÓN BD (LOCAL O AWS RDS)
# -------------------------------------------------

app.config['MYSQL_HOST'] = 'database-1.cn6coue2aon7.us-east-1.rds.amazonaws.com'  # Cambiar por tu endpoint
app.config['MYSQL_USER'] = 'admin'
app.config['MYSQL_PASSWORD'] = 'holamundo1234'
app.config['MYSQL_DB'] = 'inventario_equipos'
app.config['MYSQL_CURSORCLASS'] = 'DictCursor'

mysql = MySQL(app)

# Carpeta de imágenes
UPLOAD_FOLDER = "static/image/equipos/"
app.config["UPLOAD_FOLDER"] = UPLOAD_FOLDER

if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)


# -------------------------------------------------
# RUTA PRINCIPAL – FORMULARIO
# -------------------------------------------------
@app.route("/")
def index():
    return render_template("index.html")


# -------------------------------------------------
# REGISTRAR EQUIPO
# -------------------------------------------------
@app.route("/registrar", methods=["POST"])
def registrar():

    codigo = request.form["codigo"]
    tipo_equipo = request.form["tipo_equipo"]
    marca = request.form["marca"]
    modelo = request.form["modelo"]
    sistema = request.form["sistema"]
    almacenamiento = request.form["almacenamiento"]
    ram = request.form["ram"]
    estado = request.form["estado"]
    fecha_mantenimiento = request.form["fecha_mantenimiento"]
    fecha_registro = request.form["fecha_registro"]

    # Imagen
    imagen = request.files["imagen"]
    nombre_imagen = None

    if imagen and imagen.filename.strip():
        extension = imagen.filename.rsplit(".", 1)[-1]
        nombre_imagen = f"{codigo}_{datetime.now().strftime('%Y%m%d%H%M%S')}.{extension}"
        imagen.save(os.path.join(app.config["UPLOAD_FOLDER"], nombre_imagen))

    cursor = mysql.connection.cursor()

    cursor.execute("""
        INSERT INTO equipos 
        (codigo, tipo_equipo, marca, modelo, sistema, almacenamiento, ram, estado, fecha_mantenimiento, fecha_registro, imagen)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """, (
        codigo,
        tipo_equipo,
        marca,
        modelo,
        sistema,
        almacenamiento,
        ram,
        estado,
        fecha_mantenimiento,
        fecha_registro,
        nombre_imagen
    ))

    mysql.connection.commit()
    cursor.close()

    return redirect(url_for("index"))


# -------------------------------------------------
if __name__ == "__main__":
    app.run(debug=True)
