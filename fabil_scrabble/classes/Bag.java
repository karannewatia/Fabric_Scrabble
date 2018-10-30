import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Random;
import fabric.util.ArrayList;

/**
 * This class represents the bag in the Scrabble game.
 */
public interface Bag extends fabric.lang.Object {
    public fabric.util.ArrayList get$tiles();
    
    public fabric.util.ArrayList set$tiles(fabric.util.ArrayList val);
    
    public fabric.lang.arrays.intArray get$counts();
    
    public fabric.lang.arrays.intArray set$counts(
      fabric.lang.arrays.intArray val);
    
    public Bag Bag$();
    
    public void populateBag();
    
    public java.lang.String getTile();
    
    public java.lang.String swapTile(java.lang.String tile);
    
    public static class _Proxy extends fabric.lang.Object._Proxy implements Bag
    {
        public fabric.util.ArrayList get$tiles() {
            return ((Bag._Impl) fetch()).get$tiles();
        }
        
        public fabric.util.ArrayList set$tiles(fabric.util.ArrayList val) {
            return ((Bag._Impl) fetch()).set$tiles(val);
        }
        
        public fabric.lang.arrays.intArray get$counts() {
            return ((Bag._Impl) fetch()).get$counts();
        }
        
        public fabric.lang.arrays.intArray set$counts(
          fabric.lang.arrays.intArray val) {
            return ((Bag._Impl) fetch()).set$counts(val);
        }
        
        public Bag Bag$() { return ((Bag) fetch()).Bag$(); }
        
        public void populateBag() { ((Bag) fetch()).populateBag(); }
        
        public java.lang.String getTile() { return ((Bag) fetch()).getTile(); }
        
        public java.lang.String swapTile(java.lang.String arg1) {
            return ((Bag) fetch()).swapTile(arg1);
        }
        
        public _Proxy(Bag._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements Bag {
        public fabric.util.ArrayList get$tiles() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.tiles;
        }
        
        public fabric.util.ArrayList set$tiles(fabric.util.ArrayList val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.tiles = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.util.ArrayList tiles;
        
        public fabric.lang.arrays.intArray get$counts() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.counts;
        }
        
        public fabric.lang.arrays.intArray set$counts(
          fabric.lang.arrays.intArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.counts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.intArray counts =
          fabric.lang.WrappedJavaInlineable.$wrap({ 2, 9, 2, 2, 4, 12, 2, 3, 2,
                                                    9, 1, 1, 4, 2, 6, 8, 2, 1,
                                                    6, 4, 6, 4, 2, 2, 1, 2,
                                                    1 });
        
        public Bag Bag$() {
            this.set$tiles(
                   (fabric.util.ArrayList)
                     new fabric.util.ArrayList._Impl(
                       this.$getStore()).$getProxy());
            fabric$lang$Object$();
            return (Bag) this.$getProxy();
        }
        
        public void populateBag() {
            {
                fabric.worker.transaction.TransactionManager $tm4 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff5 = 1;
                $label0: for (boolean $commit1 = false; !$commit1; ) {
                    if ($backoff5 > 32) {
                        while (true) {
                            try {
                                java.lang.Thread.sleep($backoff5);
                                break;
                            }
                            catch (java.lang.InterruptedException $e2) {  }
                        }
                    }
                    if ($backoff5 < 5000) $backoff5 *= 2;
                    $commit1 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        for (int i = 0; i < this.get$counts().get$length();
                             i++) {
                            for (int j = 0; j < (int) this.get$counts().get(i);
                                 j++) {
                                java.lang.String temp;
                                temp =
                                  i ==
                                    0
                                    ? "*"
                                    : java.lang.Character.toString((char)
                                                                     ('a' + i -
                                                                        1));
                                this.get$tiles().
                                  add(
                                    fabric.lang.WrappedJavaInlineable.$wrap(
                                                                        temp));
                            }
                        }
                    }
                    catch (final fabric.worker.RetryException $e2) {
                        $commit1 = false;
                        continue $label0;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e2) {
                        $commit1 = false;
                        fabric.common.TransactionID $currentTid3 =
                          $tm4.getCurrentTid();
                        if ($e2.tid.isDescendantOf($currentTid3))
                            continue $label0;
                        if ($currentTid3.parent != null) throw $e2;
                        throw new InternalError(
                                ("Something is broken with transaction management. Got a signa" +
                                 "l to restart a different transaction than the one being mana" +
                                 "ged."));
                    }
                    catch (final Throwable $e2) {
                        $commit1 = false;
                        if ($tm4.checkForStaleObjects()) continue $label0;
                        throw new fabric.worker.AbortException($e2);
                    }
                    finally {
                        if ($commit1) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e2) {
                                $commit1 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e2) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid3 =
                                  $tm4.getCurrentTid();
                                if ($currentTid3 == null ||
                                      $e2.tid.isDescendantOf($currentTid3) &&
                                      !$currentTid3.equals($e2.tid))
                                    continue $label0;
                                throw $e2;
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit1) {  }
                    }
                }
            }
        }
        
        public java.lang.String getTile() {
            {
                fabric.worker.transaction.TransactionManager $tm10 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff11 = 1;
                $label6: for (boolean $commit7 = false; !$commit7; ) {
                    if ($backoff11 > 32) {
                        while (true) {
                            try {
                                java.lang.Thread.sleep($backoff11);
                                break;
                            }
                            catch (java.lang.InterruptedException $e8) {  }
                        }
                    }
                    if ($backoff11 < 5000) $backoff11 *= 2;
                    $commit7 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.util.Random rand = new java.util.Random();
                        int rand_value = rand.nextInt(this.get$tiles().size());
                        java.lang.String
                          tile =
                          (java.lang.String)
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(this.get$tiles().get(rand_value));
                        this.get$tiles().remove(rand_value);
                        return tile;
                    }
                    catch (final fabric.worker.RetryException $e8) {
                        $commit7 = false;
                        continue $label6;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e8) {
                        $commit7 = false;
                        fabric.common.TransactionID $currentTid9 =
                          $tm10.getCurrentTid();
                        if ($e8.tid.isDescendantOf($currentTid9))
                            continue $label6;
                        if ($currentTid9.parent != null) throw $e8;
                        throw new InternalError(
                                ("Something is broken with transaction management. Got a signa" +
                                 "l to restart a different transaction than the one being mana" +
                                 "ged."));
                    }
                    catch (final Throwable $e8) {
                        $commit7 = false;
                        if ($tm10.checkForStaleObjects()) continue $label6;
                        throw new fabric.worker.AbortException($e8);
                    }
                    finally {
                        if ($commit7) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e8) {
                                $commit7 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e8) {
                                $commit7 = false;
                                fabric.common.TransactionID $currentTid9 =
                                  $tm10.getCurrentTid();
                                if ($currentTid9 == null ||
                                      $e8.tid.isDescendantOf($currentTid9) &&
                                      !$currentTid9.equals($e8.tid))
                                    continue $label6;
                                throw $e8;
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit7) {  }
                    }
                }
            }
            return null;
        }
        
        public java.lang.String swapTile(java.lang.String tile) {
            {
                fabric.worker.transaction.TransactionManager $tm16 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff17 = 1;
                $label12: for (boolean $commit13 = false; !$commit13; ) {
                    if ($backoff17 > 32) {
                        while (true) {
                            try {
                                java.lang.Thread.sleep($backoff17);
                                break;
                            }
                            catch (java.lang.InterruptedException $e14) {  }
                        }
                    }
                    if ($backoff17 < 5000) $backoff17 *= 2;
                    $commit13 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.lang.String newTile = this.getTile();
                        this.get$tiles().
                          add(fabric.lang.WrappedJavaInlineable.$wrap(tile));
                        return newTile;
                    }
                    catch (final fabric.worker.RetryException $e14) {
                        $commit13 = false;
                        continue $label12;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e14) {
                        $commit13 = false;
                        fabric.common.TransactionID $currentTid15 =
                          $tm16.getCurrentTid();
                        if ($e14.tid.isDescendantOf($currentTid15))
                            continue $label12;
                        if ($currentTid15.parent != null) throw $e14;
                        throw new InternalError(
                                ("Something is broken with transaction management. Got a signa" +
                                 "l to restart a different transaction than the one being mana" +
                                 "ged."));
                    }
                    catch (final Throwable $e14) {
                        $commit13 = false;
                        if ($tm16.checkForStaleObjects()) continue $label12;
                        throw new fabric.worker.AbortException($e14);
                    }
                    finally {
                        if ($commit13) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e14) {
                                $commit13 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e14) {
                                $commit13 = false;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($currentTid15 == null ||
                                      $e14.tid.isDescendantOf($currentTid15) &&
                                      !$currentTid15.equals($e14.tid))
                                    continue $label12;
                                throw $e14;
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit13) {  }
                    }
                }
            }
            return null;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new Bag._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.tiles, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.counts, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException, java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.tiles = (fabric.util.ArrayList)
                           $readRef(fabric.util.ArrayList._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.counts = (fabric.lang.arrays.intArray)
                            $readRef(fabric.lang.arrays.intArray._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            Bag._Impl src = (Bag._Impl) other;
            this.tiles = src.tiles;
            this.counts = src.counts;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements Bag._Static {
            public _Proxy(Bag._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final Bag._Static $instance;
            
            static {
                Bag.
                  _Static.
                  _Impl
                  impl =
                  (Bag._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(Bag._Static._Impl.class);
                $instance = (Bag._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements Bag._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new Bag._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 27, -82, 92, -80, 87,
    -59, -84, -48, 30, 114, 88, 39, -61, 65, 73, 48, 117, 113, -10, -110, -84,
    3, -110, -127, 2, 88, -7, -25, -98, 80, 50, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1540883868000L;
    public static final java.lang.String jlc$ClassType$fabil =
      ("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" +
       "+i+GGkg6MxIAAA==");
}
