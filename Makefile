build:
	echo "Building project..."

test:
	echo "Running tests..."
	sudo service graph start
	sudo service graph stop
	python3 -m pytest counterTest.py
	sudo service counter start

clean:
	echo "Cleaning up..."
	chmod +x ./scripts/clean.sh
	./scripts/clean.sh

build-deb:
	echo "Building .deb package..."
	chmod +x ./scripts/debBuild.sh
	./scripts/debBuild.sh

lint-deb: build-deb
	echo "Linting the .deb package..."
	-lintian graph-v1.0.0.deb
