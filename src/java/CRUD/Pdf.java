/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import DB.DB_Connection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.sql.*;
import java.io.FileOutputStream;

/**
 *
 * @author pc
 */
public class Pdf {

    public void imprimer(String id, String year, String month) {
        try {
            /* String file_name = "C:\\Users\\pc\\Documents\\TestPdf\\test.pdf";
            Document document =new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            
            Paragraph para = new Paragraph("Teste kely an'le pdf eh");
            document.add(para);
            //add table
            PdfPTable table = new PdfPTable(3);
            PdfPCell c1 = new PdfPCell(new Phrase("Heading 1"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Heading 2"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Heading 3"));
            table.addCell(c1);
            table.setHeaderRows(1);
            
            table.addCell("1.0");
            table.addCell("1.1");
            table.addCell("1.2");
            table.addCell("2.1");
            table.addCell("2.2");
            table.addCell("2.3");
            document.add(table);
            document.close();
            System.out.println("finished");*/
            DB_Connection obj_DB_Connection = new DB_Connection();
            Connection connection = obj_DB_Connection.get_connection();
            File folder = new File("documents");
            if (!folder.exists()) {
                folder.mkdir();
            }
            int months = Integer.valueOf(month);
            String[] mois = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
            
            String client_query = "select numtel, nom, sexe, solde, age from client where id_client = ?";
            PreparedStatement client_ps = connection.prepareStatement(client_query);
            client_ps.setString(1, id);
            ResultSet client_rs = client_ps.executeQuery();
            if (client_rs.next() == true) {
                
                String contact = client_rs.getString(1);
                String nom = client_rs.getString(2);
                String sexe = client_rs.getString(3);
                int solde = client_rs.getInt(4);
                int age = client_rs.getInt(5);
                
                String date = "25/06/2024";
                String nom_fichier = "documents/Facture_de_" + nom + ".pdf";
                Font TitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
                Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
                LineSeparator ls = new LineSeparator();
                ls.setLineColor(BaseColor.BLACK);
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(nom_fichier));
                document.open();

                document.addTitle("Facture du client " + nom);
                Paragraph preface = new Paragraph();
                Paragraph titre = new Paragraph(""+mois[months - 1]+" "+year, TitleFont);
                titre.setAlignment(Element.ALIGN_CENTER);
                preface.add(titre);
                preface.add(ls);
                
                Chunk c_contact = new Chunk(contact, redFont);
                Chunk c_nom = new Chunk(nom, redFont);
                Chunk c_age = new Chunk(""+age+" ans", redFont);
                Chunk c_sexe = new Chunk(sexe, redFont);
                Chunk c_solde = new Chunk(""+solde+" Ariary", redFont);
                
                preface.add(new Paragraph(" "));
                preface.add(new Paragraph(" "));
                preface.add("Contact : ");
                preface.add(c_contact);
                
                preface.add(new Paragraph(" "));
                preface.add("Nom du client : ");
                preface.add(c_nom);
                
                preface.add(new Paragraph(" "));
                preface.add("Age : ");
                preface.add(c_age);
                
                preface.add(new Paragraph(" "));
                preface.add("Sexe : ");
                preface.add(c_sexe);
                
                preface.add(new Paragraph(" "));
                preface.add("Solde principal : ");
                preface.add(c_solde);
                
                preface.add(new Paragraph(" "));
                preface.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                PdfPCell cell;
                cell = new PdfPCell(new Phrase("Date", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Raison", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Credit", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Debit", FontFactory.getFont("Comic Sans MS", 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                table.addCell(cell);

                PreparedStatement debit_ps = connection.prepareStatement("select montant, raison, date from envoyer where (numEnv = ?) and (Month(date) = ?) AND Year(date) = ?");
                debit_ps.setString(1, id);
                debit_ps.setString(2, month);
                debit_ps.setString(3, year);
                ResultSet debit_rs = debit_ps.executeQuery();
                while (debit_rs.next()) {
                    int montant = debit_rs.getInt("montant");
                    String raison = debit_rs.getString("raison");
                    String datetime = debit_rs.getString("date");

                    cell = new PdfPCell(new Phrase(datetime, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(raison, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("" + montant, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("", FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                }
                
                PreparedStatement credit_ps = connection.prepareStatement("select montant, raison, date from envoyer where (numRec = ?) and (Month(date) = ?) AND Year(date) = ?");
                credit_ps.setString(1, id);
                credit_ps.setString(2, month);
                credit_ps.setString(3, year);
                ResultSet credit_rs = credit_ps.executeQuery();
                
                
                while (credit_rs.next()) {
                    int montant = credit_rs.getInt("montant");
                    String raison = credit_rs.getString("raison");
                    String datetime = credit_rs.getString("date");

                    cell = new PdfPCell(new Phrase(datetime, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(raison, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("", FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("" + montant, FontFactory.getFont("Comic Sans MS", 12)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.WHITE);
                    table.addCell(cell);

                }
      
                preface.add(table);
                
                PreparedStatement total_debit_ps = connection.prepareStatement("select SUM(montant) AS debit from envoyer where (numEnv = ?) and (Month(date) = ?) AND Year(date) = ?");
                total_debit_ps.setString(1, id);
                total_debit_ps.setString(2, month);
                total_debit_ps.setString(3, year);
                ResultSet total_debit_rs = total_debit_ps.executeQuery();
                if (total_debit_rs.next() == true) {
                    int total_debit = total_debit_rs.getInt(1);
                    Chunk c_debit = new Chunk(total_debit + " Ariary", redFont);

                    preface.add(new Paragraph(" "));
                    preface.add(new Paragraph(" "));
                    preface.add("Total credit : ");
                    preface.add(c_debit);
                }
                
                PreparedStatement total_credit_ps = connection.prepareStatement("select SUM(montant) AS debit from envoyer where (numRec = ?) and (Month(date) = ?) AND Year(date) = ?");
                total_credit_ps.setString(1, id);
                total_credit_ps.setString(2, month);
                total_credit_ps.setString(3, year);
                ResultSet total_credit_rs = total_credit_ps.executeQuery();
                if (total_credit_rs.next() == true) {
                    int total_credit = total_credit_rs.getInt(1);
                    Chunk c_credit = new Chunk(total_credit + " Ariary", redFont);

                    preface.add(new Paragraph(" "));
                    preface.add("Total debit : ");
                    preface.add(c_credit);
                }
                
                document.add(preface);
                document.close();

                File ouvrir = new File(nom_fichier);
                Desktop desk = Desktop.getDesktop();
                desk.open(ouvrir);
            }

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    
}
