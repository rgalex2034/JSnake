OUTDIR=build

COPTIONS=-d $(OUTDIR) -cp src/ -Xlint:deprecation -Xlint:unchecked

run: build
	cd $(OUTDIR) && java Main
build:
	javac $(COPTIONS) src/Main.java src/com/rgalex/jsnake/JSnake.java
clean:
	rm -rf $(OUTDIR)
dist: build
	jar cfe dist/JSnake.jar Main build/Main.class

# vim: tabstop=4: shiftwidth=4: noexpandtab
