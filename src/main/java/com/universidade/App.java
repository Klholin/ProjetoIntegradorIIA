package com.universidade;
public class App 
{
    public static void main( String[] args )
    {       
        ConexaoBanco con = new ConexaoBanco("mydb", "123456", "root");
        con.conectar();
        con.desconectar();
        
    }
}
