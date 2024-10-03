.PHONY: create-env activate-env run-stress-test

create-env:
	python -m virtualenv venv; \
	source venv/bin/activate; \
	pip install -r requeriments.txt

run-stress-test:
	k6 run test.js
