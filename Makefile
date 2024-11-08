.PHONY: test-new-user-create-offer

test-new-user-create-offer:
	k6 run new-user-create-offer.js
