package ru.sfedu.hiber.lab1.api;

import java.util.List;
import java.util.Optional;

public interface IMetaDataProvider {
    Optional<List<String>> getListSchemas();

    Optional<List<String>> getListTables();

    Optional<List<String>> getListTablesType();

    Optional<List<String>> getListRoleTablesGrants();
}
