package com.formation.cdb.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.formation.cdb.model.Company;
import com.formation.cdb.service.MenuActions;

public class CompanyPage extends Page<Company> {
	final Properties prop = new Properties();

	public CompanyPage(int index) {

		InputStream input = null;
		try {
			input = new FileInputStream("src.main/resource/conf.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		maxInPage = Integer.parseInt(prop.getProperty("pagination.maxpage"));
		indexMaPage = index;
		this.list = MenuActions.INSTANCE.findCompaniesInRange(index, maxInPage);
	}

	public static void main(String[] args) {
		CompanyPage page = new CompanyPage(2);
		System.out.println(page.getList());
		System.out.println(MenuActions.INSTANCE.getNbCompanies());
	}

}
