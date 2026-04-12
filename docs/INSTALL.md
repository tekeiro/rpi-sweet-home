# Installation

## Environment variables

### Executors

Concurrent executors to parallelize tasks

* **IO Executor**: For rest clients. `EXECUTOR_IO_*`

#### Executor variables

* **EXECUTOR_(executorName)_TYPE**: (Enum) Type of executor.
  * **SINGLE_THREAD**: Single thread executor, (no parallelism).
  * **FIXED_POOL**: Pool with a fixed number of (heavy) threads.
  * **VIRTUAL_THREAD_POOL**: Virtual threads.
* **EXECUTOR_(executorName)_THREADS**: (Number) Number of threads for the executor. Only used with `FIXED_POOL`

### Database

* **DB_URL**: (String) JDBC URL for the database. Format: `jdbc:databaseType://host:port/db`
* **DB_USERNAME**: (String) Username for the database.
* **DB_PASSWORD**: (String) Password for the database.
* **DB_DRIVER**: (String) JDBC driver for the database. Defaults to `org.postgresql.Driver`.

### TVDB

* **TVDB_API_BASE_URL**: (String) Base URL for the TVDB API. Defaults to `https://api.thetvdb.com/v4`
* **TVDB_API_KEY**: (String) API key for the TVDB API.
* **TVDB_API_LIMIT**: (Number) Items per page for the TVDB API.
