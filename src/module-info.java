/**
 * 
 */
/**
 * 
 */
module FinanceApp {
    requires java.desktop;
}

//The java.desktop module contains all AWT and Swing classes
//When using the module system, you need to explicitly declare which modules your application requires
//Without this declaration, you can't access Swing classes even if you import them

//This is part of Project Jigsaw's module system, where Java's core APIs are split into modules
//for better organization and encapsulation. Common modules include: 
//requires java.desktop;     // For Swing/AWT
//requires java.sql;         // For JDBC and SQL functionality
//requires java.net.http;    // For HTTP client
//requires java.logging;     // For logging utilities