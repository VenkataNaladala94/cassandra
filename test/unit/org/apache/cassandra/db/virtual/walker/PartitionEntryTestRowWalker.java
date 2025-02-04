/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.db.virtual.walker;

import org.apache.cassandra.db.virtual.model.Column;
import org.apache.cassandra.db.virtual.model.PartitionEntryTestRow;

/**
 * The {@link org.apache.cassandra.db.virtual.model.PartitionEntryTestRow} row metadata and data walker.
 *
 * @see org.apache.cassandra.db.virtual.model.PartitionEntryTestRow
 */
public class PartitionEntryTestRowWalker implements RowWalker<PartitionEntryTestRow>
{
    @Override
    public void visitMeta(MetadataVisitor visitor)
    {
        visitor.accept(Column.Type.PARTITION_KEY, "key", String.class);
        visitor.accept(Column.Type.REGULAR, "primary_key", String.class);
        visitor.accept(Column.Type.REGULAR, "secondary_key", String.class);
    }

    @Override
    public void visitRow(PartitionEntryTestRow row, RowMetadataVisitor visitor)
    {
        visitor.accept(Column.Type.PARTITION_KEY, "key", String.class, row::key);
        visitor.accept(Column.Type.REGULAR, "primary_key", String.class, row::primaryKey);
        visitor.accept(Column.Type.REGULAR, "secondary_key", String.class, row::secondaryKey);
    }

    @Override
    public int count(Column.Type type)
    {
        switch (type)
        {
            case CLUSTERING:
                return 0;
            case PARTITION_KEY:
                return 1;
            case REGULAR:
                return 2;
            default:
                throw new IllegalStateException("Unknown column type: " + type);
        }
    }
}
