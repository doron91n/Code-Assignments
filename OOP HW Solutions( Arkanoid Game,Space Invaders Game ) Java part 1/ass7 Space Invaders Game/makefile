
compile: bin
	javac -d bin -cp biuoop-1.4.jar src/*.java src/*/*.java 
run:
	java -cp biuoop-1.4.jar:bin:resources Ass7Game 
jar:
	mkdir uber-jar
	cd uber-jar; jar -xf ../biuoop-1.4.jar; rm -rf META-INF
	jar cfm space-invaders.jar Manifest.mf -C bin . -C resources . -C uber-jar .
	rm -rf uber-jar
bin:
	mkdir bin
