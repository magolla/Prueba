package com.tdil.d2d.persistence;

import java.util.Calendar;

public enum SubscriptionTimeUnit {

	DAY() {
		@Override
		public int days() {
			return 1;
		}
		@Override
		public Calendar add(Calendar cal, int units) {
			cal.add(Calendar.DATE, units);
			return cal;
		}
	}
	,
	MONTH() 
	{
		@Override
		public int days() {
			return 30;
		}
		@Override
		public Calendar add(Calendar cal, int units) {
			cal.add(Calendar.MONTH, 1);
			return cal;
		}
	},
	YEAR () {
		@Override
		public int days() {
			return 365;
		}
		
		@Override
		public Calendar add(Calendar cal, int units) {
			cal.add(Calendar.YEAR, units);
			return cal;
		}
	};
	
	
	public abstract int days();

	public abstract Calendar add(Calendar cal, int units);
}
