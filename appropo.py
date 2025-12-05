from flask import Flask, render_template, request, redirect, url_for
from flask_mysqldb import MySQL
import os
from datetime import datetime

app = Flask(__name__)

# -----------------------------
# CONFIGURACIÓN BD (LOCAL O AWS)
# -----------------------------

app.config['MYSQL_HOST'] = 'localhost'        # O endpoint de AWS RDS
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'inventario_equipos'

mysql = MySQL(app)

# -----------------------------
# CARPETA PARA GUARDAR IMÁGENES
# -----------------------------
UPLOAD_FOLDER = "static/image/equipos/"
app.config["UPLOAD_FOLDER"] = UPLOAD_FOLDER

# Crear carpeta si no existe
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

# -----------------------------------------------------
# RUTA PRINCIPAL (MUESTRA EL FORMULARIO)
# -----------------------------------------------------
@app.route("/")
def index():
    return render_template("index.html")

# -----------------------------------------------------
# GUARDAR EQUIPO EN BASE DE DATOS
# -----------------------------------------------------
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

    # ---------- MANEJO DE IMAGEN ----------
    imagen = request.files["imagen"]
    nombre_imagen = None

    if imagen and imagen.filename != "":
        extension = imagen.filename.split(".")[-1]
        nombre_imagen = f"{codigo}_{datetime.now().strftime('%Y%m%d%H%M%S')}.{extension}"
        ruta_completa = os.path.join(app.config["UPLOAD_FOLDER"], nombre_imagen)
        imagen.save(ruta_completa)

    # ---------- INSERTAR EN BD ----------
    cursor = mysql.connection.cursor()

    cursor.execute("""
        INSERT INTO equipos (codigo, tipo_equipo, marca, modelo, sistema, almacenamiento, ram, estado, imagen)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
    """, (
        codigo,
        tipo_equipo,
        marca,
        modelo,
        sistema,
        almacenamiento,
        ram,
        estado,
        nombre_imagen
    ))

    mysql.connection.commit()
    cursor.close()

    return redirect(url_for("formulario"))

# -----------------------------
if __name__ == "__main__":
    app.run(debug=True)
