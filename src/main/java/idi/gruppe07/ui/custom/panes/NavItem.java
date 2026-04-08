package idi.gruppe07.ui.custom.panes;

import javafx.scene.Node;

import java.util.function.Supplier;

public record NavItem(String label, Supplier<Node> content) {
}
