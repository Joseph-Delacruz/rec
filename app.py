from flask import Flask, render_template, request, redirect, url_for, flash
from datetime import datetime
import os

app = Flask(__name__)
app.secret_key = os.environ.get("FLASK_SECRET", "dev_secret_key")

@app.route("/", methods=["GET"])
def index():
    return render_template("index.html")

@app.route("/registrar", methods=["POST"])
def registrar():
    # obtén datos del formulario
    data = {
        "codigo": request.form.get("codigo", ""),
        "tipo_equipo": request.form.get("tipo_equipo", ""),
        "marca": request.form.get("marca", ""),
        "modelo": request.form.get("modelo", ""),
        "sistema": request.form.get("sistema", ""),
        "almacenamiento": request.form.get("almacenamiento", ""),
        "ram": request.form.get("ram", ""),
        "estado": request.form.get("estado", ""),
        "fecha_mantenimiento": request.form.get("fecha_mantenimiento", ""),
        "fecha_registro": request.form.get("fecha_registro", "")
    }
    # aquí puedes guardar a DB; por ahora solo mostramos mensaje de éxito
    flash(f"Registro guardado: código {data['codigo']}", "success")
    # para debug mostrar en consola
    print("Registro recibido:", data)
    return redirect(url_for("index"))

if __name__ == "__main__":
    # Ejecuta con: set FLASK_APP=app.py && flask run  (Windows) o python app.py
    app.run(debug=True, port=5000)
