package com.lukas8219.io.managers;

import com.lukas8219.io.config.ConfigurationUtils;
import com.lukas8219.io.utils.InputReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

public class MigrationManager {

    private static final String MIGRATION_FOLDER_BASE_NAME = ConfigurationUtils.getConfiguration("MIGRATION_FOLDER");
    private static final String MIGRATION_TABLE_NAME = ConfigurationUtils.getConfiguration("MIGRATION_TABLE");
    private static final Logger logger = LoggerFactory.getLogger(MigrationManager.class);

    static {
        //Check if migration table exists, if not create it.
        createMigrationTable();
        var folder = lookForMigrationFolder();
        if (!folder.isEmpty()) {
            var files = getAllSqlFiles(folder);
            runScripts(files);
        } else {
            logger.info("No migration folder found");
        }
    }

    private MigrationManager() {
    }

    private static void createMigrationTable() {
        var stmt = "CREATE TABLE IF NOT EXISTS %s.%s(\n" +
                "    id int NOT NULL AUTO_INCREMENT,\n" +
                "    file_name VARCHAR(255),\n" +
                "    applied_at DATETIME,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";
        if (ConnectionManager.getSelectedDatabase() == null) {
            throw new RuntimeException("Please, inform a database schema");
        }
        var query = String.format(stmt, ConnectionManager.getSelectedDatabase(), MIGRATION_TABLE_NAME);
        try {
            ConnectionManager.getConnection()
                    .createStatement()
                    .executeUpdate(query);
        } catch (SQLException e) {
            logger.error("An error occurred when trying to create the migration table", e);
        }
    }

    private static void runScripts(Collection<File> files) {
        for (var file : files) {
            try {
                var inputStream = new FileInputStream(file);
                if (!checkIfScriptAlreadyHasBeenRunned(file.getName())) {
                    var result = InputReaderUtil.readAndApplyToEntireFile(inputStream, MigrationManager::applyScript);
                    if (result) {
                        insertRegistryIntoMigrationTable(file.getName());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean checkIfScriptAlreadyHasBeenRunned(String name) {
        var query = String.format("SELECT * FROM %s.%s WHERE file_name = \"%s\"",
                ConnectionManager.getSelectedDatabase(),
                MIGRATION_TABLE_NAME,
                name);
        try {
            var set = ConnectionManager.getConnection()
                    .prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery();
            if (set.last()) {
                return set.getRow() >= 1;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred when trying to check if script has been runned already", e);
        }
    }

    private static void insertRegistryIntoMigrationTable(String fileName) {
        if (ConnectionManager.getSelectedDatabase() == null) {
            throw new RuntimeException("Please, inform a database schema in the application.properties file");
        }
        var stmt = String.format("INSERT INTO %s.%s(file_name, applied_at) VALUES(\"%s\",\"%s\")",
                ConnectionManager.getSelectedDatabase(),
                MIGRATION_TABLE_NAME,
                fileName,
                LocalDateTime.now());
        try {
            ConnectionManager.getConnection()
                    .createStatement()
                    .executeUpdate(stmt);
        } catch (SQLException e) {
            logger.error("An exception occurred when trying to insert a registry into Migration table", e);
        }
    }

    private static boolean applyScript(String script) {
        var statements = script.trim()
                .split(";");
        return Stream.of(statements)
                .map(MigrationManager::runSingleStatement)
                .allMatch(result -> result == Boolean.TRUE);
    }

    private static boolean runSingleStatement(String statement) {
        try {
            logger.debug("Running script\n{}\n", statement);
            ConnectionManager.getConnection()
                    .createStatement()
                    .executeUpdate(statement);
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("No database selected")) {
                throw new RuntimeException(e.getMessage(), e);
            } else {
                logger.error("An SQL exception occurred", e);
                return false;
            }
        }
    }

    private static Collection<File> getAllSqlFiles(Folder folder) {
        var fileList = Arrays.asList(folder.getFolder().listFiles());
        var result = new ArrayList<File>();
        for (var file : fileList) {
            if (file.isFile() && file.getName().contains(".sql")) {
                result.add(file);
            }
        }
        result.sort(Comparator.comparing(File::getName));
        return result;
    }

    public static void init() {
    }

    ;

    private static Folder lookForMigrationFolder() {
        var resource = MigrationManager.class.getClassLoader().getResource(MIGRATION_FOLDER_BASE_NAME);
        if (resource != null) {
            var folder = new File(resource.getFile());
            if (folder.exists() && folder.isDirectory()) {
                return new Folder(folder);
            } else {
                return new Folder(null);
            }
        } else {
            return new Folder(null);
        }
    }
}

class Folder {

    private final File folder;

    public Folder(File folder) {
        this.folder = folder;
    }

    public boolean isEmpty() {
        return folder == null;
    }

    public Path getPath() {
        return folder.toPath();
    }

    public File getFolder() {
        return folder;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}