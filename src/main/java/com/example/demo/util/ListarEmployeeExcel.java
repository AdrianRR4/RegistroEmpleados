package com.example.demo.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.example.demo.models.entity.Employee;

@Component("/toList.xlsx")
public class ListarEmployeeExcel extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"listado-employee.xlsx\"");
			Sheet hoja=workbook.createSheet("employees");
			
			Row	filaTitulo= hoja.createRow(0);
			Cell celda=filaTitulo.createCell(0);
			celda.setCellValue("Listdo general de empleados");
			
			Row filaData=hoja.createRow(2);
			String[] columnas= {"Id", "Nombres", "Apellidos","Fecha de nacimiento", "Email"};
			
		for (int i = 0; i < columnas.length; i++) {
			celda= filaData.createCell(i);
			celda.setCellValue(columnas[i]);
			
			List<Employee> listaC=(List<Employee>) model.get("employees");
			
			int numFila=3;
			for (Employee employee : listaC) {
				
				filaData.createCell(0).setCellValue(employee.getId());
				filaData.createCell(1).setCellValue(employee.getName());
				filaData.createCell(2).setCellValue(employee.getLastName());
				filaData.createCell(3).setCellValue(employee.getDateOfBirth());
				filaData.createCell(4).setCellValue(employee.getEmail());
				
				numFila++;
			
			}
			
		}
	}

}
