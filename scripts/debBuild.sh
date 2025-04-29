#!/bin/sh

TEMP_DIR=tmp

echo "Starting deb package build"

echo "Making temporary directory tree"
mkdir -p $TEMP_DIR
mkdir -p $TEMP_DIR/lib/systemd/system/
mkdir -p $TEMP_DIR/usr/local/bin/
mkdir -p $TEMP_DIR/DEBIAN/

echo "Copy control file for DEBIAN/"
cp src/DEBIAN/control $TEMP_DIR/DEBIAN/

echo "no conffiles for DEBIAN"

echo "Copy graph program into place"
cp -r package/ $TEMP_DIR/usr/local/bin/

echo "Compiling Java program"
javac package/src/main/java/cpsc450/hello.java -d $TEMP_DIR/usr/local/bin/

echo "Service file into place"
cp src/graph.service $TEMP_DIR/lib/systemd/system/

chmod 644 $TEMP_DIR/lib/systemd/system/graph.service

echo "Moving scripts into place"
cp src/DEBIAN/postinst $TEMP_DIR/DEBIAN/
cp src/DEBIAN/prerm $TEMP_DIR/DEBIAN/
cp src/DEBIAN/postrm $TEMP_DIR/DEBIAN/

chmod +x $TEMP_DIR/DEBIAN/postinst
chmod +x $TEMP_DIR/DEBIAN/prerm
chmod +x $TEMP_DIR/DEBIAN/postrm

echo "Building deb file"
version=$(grep '^Version:' src/DEBIAN/control | awk '{print $2}')
dpkg-deb --root-owner-group --build $TEMP_DIR
mv $TEMP_DIR.deb graph-v$version.deb

echo "Complete."
