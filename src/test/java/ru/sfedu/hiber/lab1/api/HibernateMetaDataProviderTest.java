package ru.sfedu.hiber.lab1.api;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateMetaDataProviderTest {

    @Test
    public void getListSchemasSuccess() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListSchemas().get();
        assertNotNull(result);
    }

    @Test
    public void getListSchemasFail() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListSchemas().get();
        assertNotEquals(result, null);
    }

    @Test
    public void getListTablesSuccess() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListTables().get();
        assertNotNull(result);
    }

    @Test
    public void getListTablesFail() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListTables().get();
        assertNotEquals(result, null);
    }

    @Test
    public void getListTablesTypeSuccess() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListTablesType().get();
        assertNotNull(result);
    }

    @Test
    public void getListTablesTypeFail() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListTablesType().get();
        assertNotEquals(result, null);
    }

    @Test
    public void getListRoleTablesGrantsSuccess() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListRoleTablesGrants().get();
        assertNotNull(result);
    }

    @Test
    public void getListRoleTablesGrantsFail() throws IOException {
        HibernateMetaDataProvider instance = new HibernateMetaDataProvider();
        List<String> result = instance.getListRoleTablesGrants().get();
        assertNotEquals(result, null);
    }
}