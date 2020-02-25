package ru.otus.hw14.commands;

import lombok.AllArgsConstructor;
import ru.otus.hw14.atm.ATM;

@AllArgsConstructor
public class RestoreBalanceCommand implements Command {

  @Override
  public void execute(ATM atm) {
    // TODO: передаём массив состояний и проходимся по нему
  }

  @Override
  public void undo() {
    // TODO: два мементо? список комманд и список состояний?
  }
}
