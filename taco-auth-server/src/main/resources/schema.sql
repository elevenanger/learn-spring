CREATE TABLE IF NOT EXISTS auth_user (
  "id" serial PRIMARY KEY,
	"username" varchar(20) NOT NULL,
  "password" varchar(255) NOT NULL,
	"role" varchar(30) NOT NULL
);
