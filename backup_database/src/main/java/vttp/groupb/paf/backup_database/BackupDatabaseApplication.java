package vttp.groupb.paf.backup_database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackupDatabaseApplication implements CommandLineRunner {

	@Autowired
	private EventPoller eventPoller;

	@Autowired
	private ReconcileDatabase reconcileDatabase;

	public static void main(String[] args) {
		SpringApplication.run(BackupDatabaseApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		eventPoller.pollEvents();
		reconcileDatabase.reconcileDb();
	}
}
