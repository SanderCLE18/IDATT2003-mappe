package idi.gruppe07.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Portfolio {
  private List<Share> shares;

  public Portfolio() {
    this.shares = new ArrayList<>();
  }

  public boolean addShare(Share share) {
    return this.shares.add(share);
  }

  public boolean removeShare(Share share) {
    return this.shares.remove(share);
  }

  public List<Share> getShares() {
    return this.shares;
  }

  public List<Share> getShares(String symbol){
    return shares.stream()
        .filter(n -> n.getStock().getCompany().equals(symbol))
        .collect(Collectors.toList());
  }

  public boolean contains(Share share){
    return this.shares.contains(share);
  }
}
