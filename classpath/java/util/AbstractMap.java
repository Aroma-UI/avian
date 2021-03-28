/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.util;

public abstract class AbstractMap<K, V> implements Map<K, V> {
    private AbstractSet<K> keySet;

    public V get(Object key) {
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        if (key == null)
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.getKey() == null)
                    return entry.getValue();
            }
        else {
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (key.equals(entry.getKey()))
                    return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        if (keySet == null)
            keySet = new AbstractSet<K>() {
                @Override
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private final Iterator<Entry<K, V>> iterator = entrySet().iterator();

                        @Override
                        public boolean hasNext() {
                            return iterator.hasNext();
                        }

                        @Override
                        public K next() {
                            return iterator.next().getKey();
                        }

                        @Override
                        public void remove() {
                            iterator.remove();
                        }
                    };
                }

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }
            };
        return keySet;
    }

    public static class SimpleEntry<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        public SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry))
                return false;
            @SuppressWarnings("unchecked")
            Map.Entry<K, V> that = (Map.Entry<K, V>) object;
            return java.util.Objects.equals(key, that.getKey()) && java.util.Objects.equals(value, that.getValue());
        }

        public int hashCode() {
            return Objects.hash(super.hashCode(), key, value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return key + "=" + value;
        }
    }
}
