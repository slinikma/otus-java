package ru.otus.hw14.atm;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
* Must be defined from bigger to smaller
* */
public enum NominalsUSD implements Nominals {
  HUNDRED_DOLLARS {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(100);
    }
  },
  TWENTY_DOLLARS {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(20);
    }
  },
  TEN_DOLLARS {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(10);
    }
  },
  FIVE_DOLLARS {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(5);
    }
  },
  TWO_DOLLARS {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(2);
    }
  },
  DOLLAR {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(1);
    }
  },
  HALF_DOLLAR {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(0.50).setScale(2, RoundingMode.DOWN);
    }
  },
  QUARTER {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(0.25).setScale(2, RoundingMode.DOWN);
    }
  },
  DIME {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(0.10).setScale(2, RoundingMode.DOWN);
    }
  },
  NICKEL {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(0.05).setScale(2, RoundingMode.DOWN);
    }
  },
  PENNY {
    @Override
    public BigDecimal getValue() {
      return new BigDecimal(0.01).setScale(2, RoundingMode.DOWN);
    }
  };
}
