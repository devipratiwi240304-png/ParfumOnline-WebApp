/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Devi Pratiwi
 */
public class SessionUtil {
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }
    
    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && "ADMIN".equals(session.getAttribute("userRole"));
    }
    
    public static boolean isKaryawan(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && "KARYAWAN".equals(session.getAttribute("userRole"));
    }
    
    public static Object getAttribute(HttpServletRequest request, String attribute) {
        HttpSession session = request.getSession(false);
        return session != null ? session.getAttribute(attribute) : null;
    }
    
    public static void setAttribute(HttpServletRequest request, String attribute, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(attribute, value);
    }
    
    public static void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
