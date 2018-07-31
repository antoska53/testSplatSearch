/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsplatsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author tosha
 */
public class TreePathsTreeModel implements TreeModel {
     private final ArrayList<TreePath> paths;
      private final String root;

      public TreePathsTreeModel(String root, TreePath[] paths) {
        this.root = root;
        this.paths = new ArrayList<TreePath> (Arrays.asList(paths));
      }
      
      public TreePathsTreeModel(String root, ArrayList<TreePath> paths) {
        this.root = root;
        this.paths = paths;
      }

      @Override
      public Object getRoot() {
        return this.root;
      }

      @Override
      public Object getChild(Object parent, int index) {
        try {
          return getChildren(parent).get(index);
        } catch (IndexOutOfBoundsException ex) {
          return null;
        }
      }

      @Override
      public int getChildCount(Object parent) {
        return getChildren(parent).size();
      }

      @Override
      public boolean isLeaf(Object node) {
        for (int i = 0; i < paths.size(); i++) {
          TreePath treePath = paths.get(i);
          if (treePath.getLastPathComponent().equals(node))
            return true;
        }
        return false;
      }

      // This method is only required for editable trees, so it is not
      // implemented here.
      @Override
      public void valueForPathChanged(TreePath path, Object newValue) {
        //throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public int getIndexOfChild(Object parent, Object child) {
        return getChildren(parent).indexOf(child);
      }

      // This TreeModel never fires any events (since it is not editable)
      // so event listener registration methods are left unimplemented
      @Override
      public void addTreeModelListener(TreeModelListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void removeTreeModelListener(TreeModelListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
      }
              //search all paths in list for given object 
              //return every item one level further than it
      private ArrayList<String> getChildren(Object parent) {
        ArrayList<String> children = new ArrayList<String>();
        for (int i = 0; i < this.paths.size(); i++) {
          ArrayList<Object> pathObjects = new ArrayList<Object>( Arrays.asList(this.paths.get(i).getPath()) );
          for (Iterator<Object> it = pathObjects.iterator(); it.hasNext();) {
            Object parentCandidate = it.next();
            if (parentCandidate.equals(parent)) {
              Iterator<Object> checker = it;
              try {
                String child = new DefaultMutableTreeNode( checker.next() ).toString();
                if ( !children.contains(child) )
                  children.add (child);
              } catch (NoSuchElementException ex) {

              } 
            }
          }
        }
        return children;
      }

   
}
