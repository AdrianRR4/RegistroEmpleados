package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.view.document.AbstractPdfView;
	
import com.example.demo.models.entity.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/*
 * 
 * 
 * */
@Component("/toList")
public class ListarEmployeePdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	List<Employee>listadoEmpleados=(List<Employee>) model.get("employees");
		
	
	document.setPageSize(PageSize.LETTER.rotate());
	document.setMargins(-20,-20,40,20);
	document.open();	
		
	
	PdfPTable tablaTitulo= new PdfPTable(1);
	PdfPCell celda= null;
	
	Font fuenteTitulo= FontFactory.getFont("Helvetica",16, Color.BLUE);
	
	celda= new PdfPCell(new Phrase("Listado de empleados"));
	celda.setBorder(0);
	celda.setBackgroundColor(new Color(40,190,138));
	// crear titulo, celda sin datos, estilos y colores de la fuente 
	tablaTitulo.addCell(celda);
	tablaTitulo.setSpacingAfter(30);
	celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	celda.setVerticalAlignment(Element.ALIGN_CENTER);
	celda.setPadding(30);
	
	PdfPTable tablaEmployee= new PdfPTable(5);
		
		listadoEmpleados.forEach(empleado->{
			tablaEmployee.addCell(empleado.getId().toString()	);
			tablaEmployee.addCell(empleado.getName());
			tablaEmployee.addCell(empleado.getLastName());
			tablaEmployee.addCell(empleado.getEmail());
			tablaEmployee.addCell(empleado.getDateOfBirth().toString());
		});
		
		document.add(tablaTitulo);
		document.add(tablaEmployee);
		
	}
	
	
	
	

}
