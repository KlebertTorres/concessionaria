@echo off
echo === Sistema de Concessionaria - Compilacao e Execucao ===
echo.

REM Criar diretorio lib se nao existir
if not exist "lib" mkdir lib

REM Baixar SQLite JDBC driver se nao existir
if not exist "lib\sqlite-jdbc-3.44.1.0.jar" (
    echo Baixando SQLite JDBC driver...
    curl -L -o lib\sqlite-jdbc-3.44.1.0.jar https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar
    echo OK Driver baixado com sucesso!
    echo.
)

REM Baixar SLF4J API se nao existir
if not exist "lib\slf4j-api-2.0.9.jar" (
    echo Baixando SLF4J API...
    curl -L -o lib\slf4j-api-2.0.9.jar https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar
    echo OK SLF4J API baixado com sucesso!
    echo.
)

REM Baixar SLF4J Simple implementation se nao existir
if not exist "lib\slf4j-simple-2.0.9.jar" (
    echo Baixando SLF4J Simple...
    curl -L -o lib\slf4j-simple-2.0.9.jar https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar
    echo OK SLF4J Simple baixado com sucesso!
    echo.
)

REM Compilar todos os arquivos Java
echo Compilando arquivos Java...
javac -cp ".;lib\*" *.java

if %ERRORLEVEL% EQU 0 (
    echo OK Compilacao concluida com sucesso!
    echo.
    echo Executando o sistema...
    echo ============================================
    echo.
    java -cp ".;lib\*" Main
) else (
    echo X Erro na compilacao!
    exit /b 1
)
