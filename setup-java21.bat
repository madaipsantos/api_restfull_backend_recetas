@echo off
echo ================================
echo  Configurando Java 21 para o projeto
echo ================================

REM Definir JAVA_HOME e PATH para Java 21
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM Verificar configuração
echo.
echo [INFO] Verificando configuracao do Java:
java -version
echo.
echo [INFO] Verificando configuracao do Maven:
mvn -version
echo.
echo [SUCCESS] Ambiente Java 21 configurado com sucesso!
echo [INFO] Para executar os testes: mvn test
echo [INFO] Para fazer build: mvn clean install
echo ================================