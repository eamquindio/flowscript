#!/bin/bash

# Script para ejecutar la GUI de FlowScript

echo "🚀 Iniciando FlowScript IDE..."
echo ""

# Compilar si es necesario
if [ ! -d "target/classes" ]; then
    echo "📦 Compilando proyecto..."
    mvn clean compile
fi

# Ejecutar GUI
echo "🎨 Lanzando interfaz gráfica..."
mvn exec:java -Dexec.mainClass="edu.eam.ingesoft.tlf.flowscript.gui.MainWindow"
